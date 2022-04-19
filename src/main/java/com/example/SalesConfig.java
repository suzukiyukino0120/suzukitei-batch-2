package com.example;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.format.datetime.joda.LocalDateParser;

@Configuration
@EnableBatchProcessing
public class SalesConfig {
	
	@Autowired 
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired 
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	
	private LocalDate today = LocalDate.now();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private String formattedToday = today.format(formatter);
	
	private Resource outputResource = new FileSystemResource("C:\\env\\csv\\" + formattedToday + ".csv");
	
	@Bean
	public MyBatisCursorItemReader<Reservation> reader(){
		return new MyBatisCursorItemReaderBuilder<Reservation>()
				.sqlSessionFactory(sqlSessionFactory)
				.queryId("com.example.ReservationRepository.find")
				.build();
		
	}
	
	@Bean
	public FlatFileItemWriter<Sales> writer(){
		FlatFileItemWriter<Sales> writer = new FlatFileItemWriter<>();
		writer.setResource(outputResource);
		writer.setEncoding("Shift-JIS");
		writer.setLineSeparator("\r\n");//���s
		writer.setAppendAllowed(false);//����ʃt�@�C�����쐬
		
		//�w�b�_�[
		writer.setHeaderCallback(new FlatFileHeaderCallback() {
			
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.append("���t,���㍇�v,��v��,��v���ϒP��,��������,�J�[�h����");
			}
		});
		
		//1�s���̃f�[�^
		writer.setLineAggregator(item -> {
    	StringBuilder sb = new StringBuilder();
    		sb.append(item.getDate());
    		sb.append(",");
    		sb.append(item.getTotalSales());
    		sb.append(",");
    		sb.append(item.getNumOfReservations());
    		sb.append(",");
    		sb.append(item.getAverage());
    		sb.append(",");
    		sb.append(item.getSalesOfCash());
    		sb.append(",");
    		sb.append(item.getSalesOfCredit());
        return sb.toString();
    });
		
		return writer;
	}
	
	@Bean SalesTasklet tasklet() {
		return new SalesTasklet();
	}
	
	@Bean
	public Step step(FlatFileItemWriter<Sales> writer) {
		return stepBuilderFactory.get("step")
				.tasklet(tasklet())
				.build();
	}
	
	@Bean
	public Job job(Step step) throws Exception {
		return jobBuilderFactory.get("job")
				.incrementer(new RunIdIncrementer())
				.flow(step)
				.end()
				.build();
	}

}

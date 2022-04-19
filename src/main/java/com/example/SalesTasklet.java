package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalesTasklet implements Tasklet {
	
	 @Autowired
	 ItemStreamReader<Reservation> reader;

	 @Autowired
	 ItemStreamWriter<Sales> writer;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		try {
			Reservation reservation = null;
			List<Sales> data = new ArrayList<Sales>();
		
			reader.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());//ExectionContextとはJobに使用するデータを保管しておく保管庫
			int totalSales = 0;
			int numOfReservation = 0;
			int salesOfCash = 0;
			int salesOfCredit = 0;
			while((reservation = reader.read()) != null) {
			
					totalSales += reservation.getTotalPrice();
					numOfReservation++;
					if(reservation.getPayMethod() == 0) {
						salesOfCredit++;
					}else {
						salesOfCash++;
					}
			
			}
			Sales sales = new Sales();
			sales.setDate(LocalDate.now());
			sales.setTotalSales(totalSales);
			sales.setNumOfReservations(numOfReservation);
			sales.setSalesOfCash(salesOfCash);
			sales.setSalesOfCredit(salesOfCredit);
		
			if(numOfReservation > 0) {
				sales.setAverage(Math.round(totalSales/numOfReservation));
			}else {
				sales.setAverage(0);
			}
		
			data.add(sales);
		
			writer.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
			writer.write(data);
		}finally {
			reader.close();
			writer.close();
		}
		
		return RepeatStatus.FINISHED;
	}

}

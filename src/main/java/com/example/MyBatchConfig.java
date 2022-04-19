package com.example;

import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * データベースなしのJobRepositoryを使う
 *（動作確認用に作成）
 */
@Component
public class MyBatchConfig implements BatchConfigurer {

    private static final Log LOG = LogFactory.getLog(MyBatchConfig.class);

    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;

    @PostConstruct
    public void initialize() {
        if (this.transactionManager == null) {
            LOG.info("Create ResourceLessTransactionManager.");
            this.transactionManager = new ResourcelessTransactionManager();
        }

        try {
            MapJobRepositoryFactoryBean repoFactory
                    = new MapJobRepositoryFactoryBean(this.transactionManager);
            repoFactory.afterPropertiesSet();
            this.jobRepository = repoFactory.getObject();

            MapJobExplorerFactoryBean explFactory
                    = new MapJobExplorerFactoryBean(repoFactory);
            explFactory.afterPropertiesSet();

            this.jobExplorer = explFactory.getObject();
            this.jobLauncher = createJobLauncher();
        } catch (Exception ex) {
            LOG.fatal(ex.getMessage(), ex);
            throw new BatchConfigurationException(ex);
        }

    }

    protected JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        launcher.afterPropertiesSet();
        return launcher;
    }

    @Override
    public JobRepository getJobRepository() throws Exception {
        return jobRepository;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return transactionManager;
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        return jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        return jobExplorer;
    }
}
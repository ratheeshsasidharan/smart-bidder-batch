package com.smartbidderbatch.config;

import com.smartbidderbatch.JobCompletionNotificationListener;
import com.smartbidderbatch.ProjectBidSelectProcessor;
import com.smartbidderbatch.domain.Project;
import com.smartbidderbatch.repository.ProjectBidRepository;
import com.smartbidderbatch.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ProjectRepository projectRepository;
    private final ProjectBidRepository projectBidRepository;
    private final ProjectBidSelectProcessor projectBidSelectProcessor;

    @Bean
    public RepositoryItemReader<Project> reader() {
        RepositoryItemReader<Project> reader = new RepositoryItemReader<>();
        reader.setRepository(projectRepository);
        reader.setMethodName("findOpenProjectsWithPastDueDate");
        reader.setPageSize(100);
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        reader.setSort(sorts);
        return reader;
    }

    public class NoOpItemWriter implements ItemWriter {
        @Override
        public void write(List list) throws Exception {
            //do nothing
        }
    }

    @Bean
    public NoOpItemWriter writer() {
        return new NoOpItemWriter();
    }


    @Bean
    public Job projectBidSelectionJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("projectBidSelectionJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemWriter writer) {
        return stepBuilderFactory.get("step1")
                .<Project, Void> chunk(10)
                .reader(reader())
                .processor(projectBidSelectProcessor)
                .writer(writer)
                .build();
    }



}

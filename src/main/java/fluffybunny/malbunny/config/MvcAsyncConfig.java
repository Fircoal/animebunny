package fluffybunny.malbunny.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class MvcAsyncConfig implements AsyncConfigurer {
     
    @Override
    public Executor getAsyncExecutor() {
    	ThreadPoolTaskExecutor exe = new ThreadPoolTaskExecutor();
        exe.setMaxPoolSize(5);
        exe.initialize();
        return exe;
    }
     
}
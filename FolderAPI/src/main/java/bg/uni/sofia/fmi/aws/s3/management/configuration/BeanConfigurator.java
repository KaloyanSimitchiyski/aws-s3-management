package bg.uni.sofia.fmi.aws.s3.management.configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurator {

    @Bean
    public AmazonS3 amazonS3Client(@Value("${aws.region}") String awsRegion) {
        return AmazonS3ClientBuilder.standard().withRegion(awsRegion).build();
    }
}

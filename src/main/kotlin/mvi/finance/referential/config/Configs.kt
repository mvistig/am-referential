package mvi.finance.referential.config

import com.fasterxml.jackson.databind.DeserializationFeature
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class JacksonConfig{
    @Bean
    fun addCustomizingToObjectMapper() : Jackson2ObjectMapperBuilderCustomizer =
            Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
                builder.featuresToDisable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES,
                                          DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            }
}


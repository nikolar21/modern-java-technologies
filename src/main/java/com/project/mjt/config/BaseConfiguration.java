package com.project.mjt.config;


import com.project.mjt.dto.FileDTO;
import com.project.mjt.models.F1Team;
import com.project.mjt.models.File;
import lombok.SneakyThrows;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;

@Configuration
public class BaseConfiguration {

    @Bean
    public ModelMapper modelMapper()
    {
        ModelMapper mapper = new ModelMapper();

        TypeMap<FileDTO,File> propertyMapper = mapper.createTypeMap(FileDTO.class, File.class);


        propertyMapper.addMappings(
                m -> m.map(src ->
                        src.getStartDateOfFile() != null ? new Timestamp(src.getStartDateOfFile().getTime()) : null,
                        File::setStartDateOfFile));

        propertyMapper.addMappings(
                m -> m.map(src ->
                                src.getEndDateOfFile() != null ? new Timestamp(src.getEndDateOfFile().getTime()) : null,
                        File::setEndDateOfFile));

        return mapper;
    }
}

package com.example.cloud_storage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileRS {

    private String filename;
    private Long size;
}

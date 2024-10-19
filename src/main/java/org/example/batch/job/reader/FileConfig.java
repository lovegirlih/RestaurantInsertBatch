package org.example.batch.job.reader;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileConfig {

  private String excelPath;

  // Getter와 Setter
  public String getExcelPath() {
    return excelPath;
  }

  public void setExcelPath(String excelPath) {
    this.excelPath = excelPath;
  }

}
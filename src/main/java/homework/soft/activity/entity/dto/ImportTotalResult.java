package homework.soft.activity.entity.dto;

import lombok.Data;

import java.util.List;


@Data
public class ImportTotalResult {
    private List<ImportRowResult> success;
    private List<ImportRowResult> failed;
}

package pl.lodz.p.it.carrental.dto;

import java.util.List;

public class BasicStatsDto {
    private List<Object> data;
    private List<String> labels;

    public BasicStatsDto() {
    }

    public BasicStatsDto(List<Object> data, List<String> labels) {
        this.data = data;
        this.labels = labels;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}

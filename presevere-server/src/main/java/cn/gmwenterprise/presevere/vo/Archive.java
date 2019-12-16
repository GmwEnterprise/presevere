package cn.gmwenterprise.presevere.vo;

import cn.gmwenterprise.presevere.common.LinkedHashMapArrayList;
import cn.gmwenterprise.presevere.domain.ArticleMetadata;

import java.util.List;

public class Archive {
    private List<Integer> historyYears;
    private Integer currentYear;
    private LinkedHashMapArrayList<Integer, ArticleMetadata> byMonth;

    public List<Integer> getHistoryYears() {
        return historyYears;
    }

    public void setHistoryYears(List<Integer> historyYears) {
        this.historyYears = historyYears;
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    public LinkedHashMapArrayList<Integer, ArticleMetadata> getByMonth() {
        return byMonth;
    }

    public void setByMonth(LinkedHashMapArrayList<Integer, ArticleMetadata> byMonth) {
        this.byMonth = byMonth;
    }
}

package com.test.heady.headytest.networking.PojoModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RankingPojoModel {

    @SerializedName("ranking")
    public String ranking;
    @SerializedName("products")
    public List<RankingProductPojoModel> rankingProductPojoModel;

    public RankingPojoModel(String ranking, List<RankingProductPojoModel> rankingProductPojoModel) {
        this.ranking = ranking;
        this.rankingProductPojoModel = rankingProductPojoModel;
    }

    public RankingPojoModel() {
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<RankingProductPojoModel> getRankingProductPojoModel() {
        return rankingProductPojoModel;
    }

    public void setRankingProductPojoModel(List<RankingProductPojoModel> rankingProductPojoModel) {
        this.rankingProductPojoModel = rankingProductPojoModel;
    }
}

package com.poliana.bills.entities;

public class BillHistory {
    private String housePassageResult;
    private String housePassageResultAt;
    private String senateClotureResult;
    private String senateClotureResultAt;
    private String senatePassageResult;
    private String senatePassageResultAt;
    private boolean awaitingSignature;
    private boolean enacted;
    private boolean vetoed;
    private String enactedAt;

    public String getHousePassageResult() {
        return housePassageResult;
    }

    public void setHousePassageResult(String housePassageResult) {
        this.housePassageResult = housePassageResult;
    }

    public String getHousePassageResultAt() {
        return housePassageResultAt;
    }

    public void setHousePassageResultAt(String housePassageResultAt) {
        this.housePassageResultAt = housePassageResultAt;
    }

    public String getSenateClotureResult() {
        return senateClotureResult;
    }

    public void setSenateClotureResult(String senateClotureResult) {
        this.senateClotureResult = senateClotureResult;
    }

    public String getSenateClotureResultAt() {
        return senateClotureResultAt;
    }

    public void setSenateClotureResultAt(String senateClotureResultAt) {
        this.senateClotureResultAt = senateClotureResultAt;
    }

    public String getSenatePassageResult() {
        return senatePassageResult;
    }

    public void setSenatePassageResult(String senatePassageResult) {
        this.senatePassageResult = senatePassageResult;
    }

    public String getSenatePassageResultAt() {
        return senatePassageResultAt;
    }

    public void setSenatePassageResultAt(String senatePassageResultAt) {
        this.senatePassageResultAt = senatePassageResultAt;
    }

    public boolean isAwaitingSignature() {
        return awaitingSignature;
    }

    public void setAwaitingSignature(boolean awaitingSignature) {
        this.awaitingSignature = awaitingSignature;
    }

    public boolean isEnacted() {
        return enacted;
    }

    public void setEnacted(boolean enacted) {
        this.enacted = enacted;
    }

    public boolean isVetoed() {
        return vetoed;
    }

    public void setVetoed(boolean vetoed) {
        this.vetoed = vetoed;
    }

    public String getEnactedAt() {
        return enactedAt;
    }

    public void setEnactedAt(String enactedAt) {
        this.enactedAt = enactedAt;
    }
}

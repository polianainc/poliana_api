package com.poliana.legislation.entities.bills;

import com.google.code.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
public class BillHistory {

    private boolean active;
    @Property("active_at")
    private int activeAt;
    @Property("awaiting_signature")
    private boolean awaitingSignature;
    @Property("awaiting_signature_since")
    private int awaitingSignatureSince;
    private boolean enacted;
    @Property("enacted_at")
    private int enactedAt;
    @Property("house_passage_result")
    private String housePassageResult;
    @Property("house_passage_result_at")
    private int housePassageResultAt;
    @Property("senate_passage_result")
    private String senatePassageResult;
    @Property("senate_passage_result_at")
    private int senatePassageResultAt;
    @Property("house_override_result")
    private String houseOverrideResult;
    @Property("senate_override_result")
    private String senateOverrideResult;
    private boolean vetoed;
    @Property("vetoed_at")
    private int vetoedAt;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getActiveAt() {
        return activeAt;
    }

    public void setActiveAt(int activeAt) {
        this.activeAt = activeAt;
    }

    public boolean isAwaitingSignature() {
        return awaitingSignature;
    }

    public void setAwaitingSignature(boolean awaitingSignature) {
        this.awaitingSignature = awaitingSignature;
    }

    public int getAwaitingSignatureSince() {
        return awaitingSignatureSince;
    }

    public void setAwaitingSignatureSince(int awaitingSignatureSince) {
        this.awaitingSignatureSince = awaitingSignatureSince;
    }

    public boolean isEnacted() {
        return enacted;
    }

    public void setEnacted(boolean enacted) {
        this.enacted = enacted;
    }

    public int getEnactedAt() {
        return enactedAt;
    }

    public void setEnactedAt(int enactedAt) {
        this.enactedAt = enactedAt;
    }

    public String getHousePassageResult() {
        return housePassageResult;
    }

    public void setHousePassageResult(String housePassageResult) {
        this.housePassageResult = housePassageResult;
    }

    public int getHousePassageResultAt() {
        return housePassageResultAt;
    }

    public void setHousePassageResultAt(int housePassageResultAt) {
        this.housePassageResultAt = housePassageResultAt;
    }

    public String getSenatePassageResult() {
        return senatePassageResult;
    }

    public void setSenatePassageResult(String senatePassageResult) {
        this.senatePassageResult = senatePassageResult;
    }

    public int getSenatePassageResultAt() {
        return senatePassageResultAt;
    }

    public void setSenatePassageResultAt(int senatePassageResultAt) {
        this.senatePassageResultAt = senatePassageResultAt;
    }

    public String getHouseOverrideResult() {
        return houseOverrideResult;
    }

    public void setHouseOverrideResult(String houseOverrideResult) {
        this.houseOverrideResult = houseOverrideResult;
    }

    public String getSenateOverrideResult() {
        return senateOverrideResult;
    }

    public void setSenateOverrideResult(String senateOverrideResult) {
        this.senateOverrideResult = senateOverrideResult;
    }

    public boolean isVetoed() {
        return vetoed;
    }

    public void setVetoed(boolean vetoed) {
        this.vetoed = vetoed;
    }

    public int getVetoedAt() {
        return vetoedAt;
    }

    public void setVetoedAt(int vetoedAt) {
        this.vetoedAt = vetoedAt;
    }
}

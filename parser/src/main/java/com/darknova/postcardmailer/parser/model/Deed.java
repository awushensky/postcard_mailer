package com.darknova.postcardmailer.parser.model;

import java.util.Date;

/**
 * A deed record.
 */
public class Deed {

    private final String documentId;
    private final String firstPartyName;
    private final String firstCrossPartyName;
    private final String instrumentNumber;
    private final String documentType;
    private final String modifier;
    private final Date recordDate;
    private final String parcelNumber;
    private final String remarks;
    private final float totalValue;

    public Deed(final String documentId, final String firstPartyName, final String firstCrossPartyName,
                final String instrumentNumber, final String documentType, final String modifier, final Date recordDate,
                final String parcelNumber, final String remarks, final float totalValue) {
        this.documentId = documentId;
        this.firstPartyName = firstPartyName;
        this.firstCrossPartyName = firstCrossPartyName;
        this.instrumentNumber = instrumentNumber;
        this.documentType = documentType;
        this.modifier = modifier;
        this.recordDate = recordDate;
        this.parcelNumber = parcelNumber;
        this.remarks = remarks;
        this.totalValue = totalValue;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getFirstPartyName() {
        return firstPartyName;
    }

    public String getFirstCrossPartyName() {
        return firstCrossPartyName;
    }

    public String getInstrumentNumber() {
        return instrumentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getModifier() {
        return modifier;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public String getParcelNumber() {
        return parcelNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public float getTotalValue() {
        return totalValue;
    }

    @Override
    public String toString() {
        return "Deed{" +
                "documentId='" + documentId + '\'' +
                ", firstPartyName='" + firstPartyName + '\'' +
                ", firstCrossPartyName='" + firstCrossPartyName + '\'' +
                ", instrumentNumber='" + instrumentNumber + '\'' +
                ", documentType='" + documentType + '\'' +
                ", modifier='" + modifier + '\'' +
                ", recordDate=" + recordDate +
                ", parcelNumber='" + parcelNumber + '\'' +
                ", remarks='" + remarks + '\'' +
                ", totalValue=" + totalValue +
                '}';
    }
}

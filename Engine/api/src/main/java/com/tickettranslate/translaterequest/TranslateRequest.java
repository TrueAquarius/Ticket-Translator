package com.tickettranslate.translaterequest;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


// https://www.mongodb.com/compatibility/spring-boot

@Document("translate_requests")
public class TranslateRequest {
    @Id
    private String id;
    private Instant received;
    private String sourceSystemID;
    private String ticketID;
    private TranslateStatus status;
    private Instant lastModified;
    private int owningProcess;

    public TranslateRequest(Instant received, String source, String ticket, TranslateStatus status, Instant lastModified, int owningProcess) {
        super();
        this.received = received;
        this.sourceSystemID = source;
        this.ticketID = ticket;
        this.status = status;
        this.lastModified = lastModified;
        this.owningProcess = owningProcess;
    }

    public TranslateRequest(String id, Instant received, String source, String ticket, TranslateStatus status, Instant lastModified, int owningProcess) {
        super();
        this.id = id;
        this.received = received;
        this.sourceSystemID = source;
        this.ticketID = ticket;
        this.status = status;
        this.lastModified = lastModified;
        this.owningProcess = owningProcess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getReceived() {
        return received;
    }

    public void setReceived(Instant received) {
        this.received = received;
    }

    public String getSourceSystemID() {
        return sourceSystemID;
    }

    public void setSourceSystemID(String sourceSystemID) {
        this.sourceSystemID = sourceSystemID;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public TranslateStatus getStatus() {
        return status;
    }

    public void setStatus(TranslateStatus status) {
        this.status = status;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public int getOwningProcess() {
        return owningProcess;
    }

    public void setOwningProcess(int owningProcess) {
        this.owningProcess = owningProcess;
    }
}

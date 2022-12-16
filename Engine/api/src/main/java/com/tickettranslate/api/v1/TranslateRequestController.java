package com.tickettranslate.api.v1;

import com.tickettranslate.translaterequest.TranslateRequest;
import com.tickettranslate.translaterequest.TranslateRequestRepository;
import com.tickettranslate.translaterequest.TranslateRequestService;
import com.tickettranslate.translaterequest.TranslateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping(path = "api/v1")
public class TranslateRequestController {

    private final TranslateRequestService translateRequestService;
    private final TranslateRequestRepository translateRequestQueue;

    @Value("${Translation.AttemptImmediately}")
    private Boolean attemptImmediately;

    @Value("${Translation.UseQueue}")
    private Boolean useQueue;

    @Autowired
    public TranslateRequestController(TranslateRequestService translationService, TranslateRequestRepository translateRequestQueue) {
        this.translateRequestService = translationService;
        this.translateRequestQueue = translateRequestQueue;
    }

    @GetMapping(path = "translate/{sourceID}/{ticketID}")
    public String translate(@PathVariable("sourceID") String sourceID, @PathVariable("ticketID") String ticketID)
    {
        // Take a time stamp for later use
        Instant now = Instant.now();
        boolean success = false;

        if(attemptImmediately)
        {
            success = translateRequestService.translate(sourceID, ticketID);
        }

        // Queue the item if the queue is to be used
        if(useQueue && !success)
        {
            TranslateRequest t = translateRequestQueue.save(new TranslateRequest(now, sourceID, ticketID, TranslateStatus.NEW, now, 0));
            success = (t != null);
        }
        return success?"Success!":"FAILURE";
    }
}

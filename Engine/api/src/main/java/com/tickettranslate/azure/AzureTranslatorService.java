package com.tickettranslate.azure;

import com.tickettranslate.core.TranslatorService;
import org.springframework.stereotype.Service;


@Service
public class AzureTranslatorService implements TranslatorService {
    @Override
    public String translate(String text) {
        return "This is the translation of (" + text + ")";
    }
}

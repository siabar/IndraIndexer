package org.lambda3.indra.indexer;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class CorpusMetadataBuilder {

    static final String CORPUS_NAME = "corpusName";
    static final String LANGUAGE = "language";
    static final String DESCRIPTION = "description";
    static final String ENCODING = "encoding";
    static final String APPLY_STEMMER = "applyStemmer";
    static final String REMOVE_ACCENTS = "removeAccents";
    static final String APPLY_LOWERCASE = "applyLowercase";
    static final String REPLACE_NUMBERS = "replaceNumbers";
    static final String STOP_WORDS = "stopWords";
    static final String MIN_TOKEN_LENGTH = "minTokenLength";
    static final String MAX_TOKEN_LENGTH = "maxTokenLength";
    static final String TRANSFORMERS = "transformers";

    private Map<String, Object> data = new HashMap<>();

    private CorpusMetadataBuilder(String corpusName, String language) {
        data.put(CORPUS_NAME, corpusName);
        data.put(LANGUAGE, language);
        data.putAll(getDefaultData());
    }

    static Map<String, Object> getDefaultData() {
        Map<String, Object> defaultData = new HashMap<>();

        defaultData.put(DESCRIPTION, null);
        defaultData.put(ENCODING, StandardCharsets.UTF_8.name());
        defaultData.put(APPLY_STEMMER, 0l);
        defaultData.put(REMOVE_ACCENTS, true);
        defaultData.put(APPLY_LOWERCASE, true);
        defaultData.put(REPLACE_NUMBERS, true);
        defaultData.put(STOP_WORDS, Collections.EMPTY_SET);
        defaultData.put(MIN_TOKEN_LENGTH, 1l);
        defaultData.put(MAX_TOKEN_LENGTH, 100l);
        defaultData.put(TRANSFORMERS, Collections.EMPTY_MAP);

        return defaultData;
    }

    public static CorpusMetadataBuilder newCorpusMetadata(String corpusName, String language) {
        return new CorpusMetadataBuilder(corpusName, language);
    }

    public CorpusMetadataBuilder desc(String description) {
        data.put(DESCRIPTION, description);
        return this;
    }

    public CorpusMetadataBuilder encoding(String encoding) {
        data.put(ENCODING, encoding);
        return this;
    }

    public CorpusMetadataBuilder applyStemmer(long times) {
        data.put(APPLY_STEMMER, times);
        return this;
    }

    public CorpusMetadataBuilder removeAccents(boolean removeAccents) {
        data.put(REMOVE_ACCENTS, removeAccents);
        return this;
    }

    public CorpusMetadataBuilder applyLowercase(boolean applyLowercase) {
        data.put(APPLY_LOWERCASE, applyLowercase);
        return this;
    }

    public CorpusMetadataBuilder replaceNumbers(boolean replaceNumbers) {
        data.put(REPLACE_NUMBERS, replaceNumbers);
        return this;
    }

    public CorpusMetadataBuilder stopWords(Set<String> stopWords) {
        data.put(STOP_WORDS, stopWords);
        return this;
    }

    public CorpusMetadataBuilder minTokenLength(long minTokenLength) {
        data.put(MIN_TOKEN_LENGTH, minTokenLength);
        return this;
    }

    public CorpusMetadataBuilder maxTokenLength(long maxTokenLength) {
        data.put(MAX_TOKEN_LENGTH, maxTokenLength);
        return this;
    }

    public CorpusMetadataBuilder transformers(Map<String, Collection<String>> transformers) {
        data.put(TRANSFORMERS, transformers);
        return this;
    }

    public CorpusMetadata build() {
        this.data = Collections.unmodifiableMap(data);
        return new CorpusMetadata(data);
    }
}

## INDRA LOADER
The INDRA Loader is divided into three modules:
1. **Preprocessing**
2. **Indra-Indexer** 
3. **Loader**

###  Preprocessing

The corpus pre-processor is responsible for defining the tokenisation strategy and the tokens’ subsequent transformations. It defines, for example, if *United States of America* corresponds to aunique token or to multiple. Stem and lowercase are two other popular transformations also supported by the pre-processor, whose full list is shown in the below table.

|**Parameter**|**Description/Options**|
|---|---|
|*input format*|Wikipedia-dump format or plain texts from one or multiple files.|
|*language*|14 supported languages.|
|*set of stopwords*|a set of tokens to be removed.|
|*set of multi-word expressions*|set of sequences of tokens that should be considered a unique token.|
|*apply lowercase*|lowercase the tokens.|
|*apply stemmer*|applies the Poter Stemmer in the tokens.|
|*remove accents*|remove the accents of words.|
|*replace numbers*|replaces numbers for the place holder <NUMBER>.|
|*min*|set a minimum acceptable token size.|
|*max*|set a maximum acceptable token size.|

### Indra-Indexer

**Indra-Indexer** is the module responsible for the generation of word embedding models. It defines a unified interface to generate predictive-based (e.g. Skip-gram and GloVe) and count-based (e.g. LSA and ESA) models whose implementation comes from the libraries DeepLearning4J and S-Space respectively.
In addition to the unification of the interface, **Indra-Indexer** integrates a corpus preprocessor package.

The final generated model stores the set of applied transformations as a metadata information. During the consumption, **[INDRA](https://github.com/Lambda-3/Indra)** applies the same set of options to guarantee consistence. For instance, admitting that a given model was generated by applying the stemmer and lowercase to the tokens. 


### Loader

**Loader** is the module responsible for uploading the generated word embedding models into the INDRA database.

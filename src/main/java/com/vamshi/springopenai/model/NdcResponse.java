package com.vamshi.springopenai.model;

import java.util.List;

public record NdcResponse(

        List<NdcRecord> drugs,

        String disclaimer,

        String fdaReferenceUrl,

        int totalResults
) {
    // Factory method for convenience
    public static NdcResponse of(List<NdcRecord> drugs) {
        return new NdcResponse(
                drugs,
                "AI-generated NDC data may be outdated or inaccurate. Always verify before clinical use.",
                "https://www.accessdata.fda.gov/scripts/cder/ndc/",
                drugs.size()
        );
    }
}
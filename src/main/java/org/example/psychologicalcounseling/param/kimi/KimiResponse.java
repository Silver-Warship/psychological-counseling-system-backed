package org.example.psychologicalcounseling.param.kimi;

import lombok.Data;
import java.util.Vector;

@Data
public class KimiResponse {
    private String id;
    private String object;
    private int created;
    private String model;
    private Vector<KimiConversation> choices;
    private KimiUsage usage;
}

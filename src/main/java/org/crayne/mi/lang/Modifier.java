package org.crayne.mi.lang;

import org.crayne.mi.parsing.ast.NodeType;
import org.jetbrains.annotations.NotNull;

public enum Modifier {
    PUBLIC, PRIVATE, PROTECTED, OWN, MUTABLE, CONSTANT, NULLABLE, NONNULL;

    public static Modifier of(@NotNull final NodeType nodeType) {
        return switch (nodeType) {
            case LITERAL_PUB -> PUBLIC;
            case LITERAL_PRIV -> PRIVATE;
            case LITERAL_PROT -> PROTECTED;
            case LITERAL_OWN -> OWN;
            case LITERAL_MUT -> MUTABLE;
            case LITERAL_CONST -> CONSTANT;
            case LITERAL_NULLABLE -> NULLABLE;
            case LITERAL_NONNULL -> NONNULL;
            default -> null;
        };
    }

}

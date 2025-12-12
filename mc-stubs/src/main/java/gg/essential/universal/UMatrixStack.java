package gg.essential.universal;

/**
 * Stub for UniversalCraft's UMatrixStack.
 */
public class UMatrixStack {
    public static class Compat {
        public static final String DEPRECATED = "Use UMatrixStack directly";
        
        public static UMatrixStack get() {
            return new UMatrixStack();
        }
        
        public static void runLegacyMethod(UMatrixStack matrixStack, Runnable runnable) {
            runnable.run();
        }
    }
}

package gg.essential.elementa.constraints.animation;

/**
 * Most of the basic animations that someone would want to use.
 *
 * If you're not sure what these algorithms look like,
 * use the easings.net website as a reference.
 */
public enum Animations implements AnimationStrategy {
    LINEAR {
        @Override
        public float getValue(float percentComplete) {
            return percentComplete;
        }
    },
    IN_QUAD {
        @Override
        public float getValue(float percentComplete) {
            return (float) Math.pow(percentComplete, 2);
        }
    },
    OUT_QUAD {
        @Override
        public float getValue(float percentComplete) {
            return -percentComplete * (percentComplete - 2);
        }
    },
    IN_OUT_QUAD {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete * 2;
            if (t < 1) return 0.5f * (float) Math.pow(t, 2);
            t--;
            return -0.5f * (t * (t - 2) - 1);
        }
    },
    IN_CUBIC {
        @Override
        public float getValue(float percentComplete) {
            return (float) Math.pow(percentComplete, 3);
        }
    },
    OUT_CUBIC {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete - 1;
            return (float) Math.pow(t, 3) + 1;
        }
    },
    IN_OUT_CUBIC {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete * 2;
            if (t < 1) return 0.5f * (float) Math.pow(t, 3);
            t -= 2;
            return 0.5f * ((float) Math.pow(t, 3) + 2);
        }
    },
    IN_QUART {
        @Override
        public float getValue(float percentComplete) {
            return (float) Math.pow(percentComplete, 4);
        }
    },
    OUT_QUART {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete - 1;
            return -((float) Math.pow(t, 4) - 1);
        }
    },
    IN_OUT_QUART {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete * 2;
            if (t < 1) return 0.5f * (float) Math.pow(t, 4);
            t -= 2;
            return -0.5f * ((float) Math.pow(t, 4) - 2);
        }
    },
    IN_QUINT {
        @Override
        public float getValue(float percentComplete) {
            return (float) Math.pow(percentComplete, 5);
        }
    },
    OUT_QUINT {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete - 1;
            return (float) Math.pow(t, 5) + 1;
        }
    },
    IN_OUT_QUINT {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete * 2;
            if (t < 1) return 0.5f * (float) Math.pow(t, 5);
            t -= 2;
            return 0.5f * ((float) Math.pow(t, 5) + 2);
        }
    },
    IN_SIN {
        @Override
        public float getValue(float percentComplete) {
            return (float) (-Math.cos(percentComplete * (Math.PI / 2)) + 1);
        }
    },
    OUT_SIN {
        @Override
        public float getValue(float percentComplete) {
            return (float) Math.sin(percentComplete * (Math.PI / 2));
        }
    },
    IN_OUT_SIN {
        @Override
        public float getValue(float percentComplete) {
            return (float) (-0.5 * (Math.cos(Math.PI * percentComplete) - 1));
        }
    },
    IN_EXP {
        @Override
        public float getValue(float percentComplete) {
            if (percentComplete <= 0f) return 0f;
            return (float) Math.pow(2, 10 * (percentComplete - 1));
        }
    },
    OUT_EXP {
        @Override
        public float getValue(float percentComplete) {
            if (percentComplete >= 1f) return 1f;
            return (float) (-Math.pow(2, -10 * percentComplete) + 1);
        }
    },
    IN_OUT_EXP {
        @Override
        public float getValue(float percentComplete) {
            if (percentComplete < 0.5) return IN_EXP.getValue(percentComplete * 2) / 2;
            return OUT_EXP.getValue((percentComplete * 2) - 1) / 2 + 0.5f;
        }
    },
    IN_CIRCULAR {
        @Override
        public float getValue(float percentComplete) {
            return (float) -(Math.sqrt(1 - Math.pow(percentComplete, 2)) - 1);
        }
    },
    OUT_CIRCULAR {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete - 1;
            return (float) Math.sqrt(1 - Math.pow(t, 2));
        }
    },
    IN_OUT_CIRCULAR {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete * 2;
            if (t < 1) return -0.5f * (float) (Math.sqrt(1 - Math.pow(t, 2)) - 1);
            t -= 2;
            return 0.5f * (float) (Math.sqrt(1 - Math.pow(t, 2)) + 1);
        }
    },
    IN_ELASTIC {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete - 1;
            return (float) (-(Math.pow(2, 10 * t)) * Math.sin((t - 0.075f) * (2 * Math.PI) / 0.3f));
        }
    },
    OUT_ELASTIC {
        @Override
        public float getValue(float percentComplete) {
            return (float) (Math.pow(2, -10 * percentComplete) * Math.sin((percentComplete - 0.075f) * (2 * Math.PI) / 0.3f) + 1);
        }
    },
    IN_OUT_ELASTIC {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete * 2f - 1;
            if (t < 0) return (float) (0.5f * -(Math.pow(2, 10 * t)) * Math.sin((t - 0.1125f) * (2 * Math.PI) / 0.45f));
            return (float) (0.5f * Math.pow(2, -10 * t) * Math.sin((t - 0.1125f) * (2 * Math.PI) / 0.45f) + 1);
        }
    },
    IN_BOUNCE {
        @Override
        public float getValue(float percentComplete) {
            return 1 - OUT_BOUNCE.getValue(1 - percentComplete);
        }
    },
    OUT_BOUNCE {
        @Override
        public float getValue(float percentComplete) {
            float t = percentComplete;
            if (t < 1 / 2.75f) {
                return 7.5625f * t * t;
            } else if (t < 2 / 2.75f) {
                t -= 1.5f / 2.75f;
                return 7.5625f * t * t + 0.75f;
            } else if (t < 2.5 / 2.75) {
                t -= 2.25f / 2.75f;
                return 7.5625f * t * t + 0.9375f;
            } else {
                t -= 2.625f / 2.75f;
                return 7.5625f * t * t + 0.984375f;
            }
        }
    },
    IN_OUT_BOUNCE {
        @Override
        public float getValue(float percentComplete) {
            if (percentComplete < 0.5f) return IN_BOUNCE.getValue(percentComplete * 2) * 0.5f;
            return OUT_BOUNCE.getValue(percentComplete * 2 - 1) * 0.5f + 0.5f;
        }
    }
}

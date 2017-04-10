package com.nickr.IoT.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LightPref {

        @SerializedName("colour")
        @Expose
        private Colour colour;
        @SerializedName("saturation")
        @Expose
        private String saturation;
        @SerializedName("brightness")
        @Expose
        private String brightness;
        @SerializedName("actionMethod")
        @Expose
        private String actionMethod;
        @SerializedName("actionPriority")
        @Expose
        private String actionPriority;

        public Colour getColour() {
            return colour;
        }

        public void setColour(Colour colour) {
            this.colour = colour;
        }

        public String getSaturation() {
            return saturation;
        }

        public void setSaturation(String saturation) {
            this.saturation = saturation;
        }

        public String getBrightness() {
            return brightness;
        }

        public void setBrightness(String brightness) {
            this.brightness = brightness;
        }

        public String getActionMethod() {
            return actionMethod;
        }

        public void setActionMethod(String actionMethod) {
            this.actionMethod = actionMethod;
        }

        public String getActionPriority() {
            return actionPriority;
        }

        public void setActionPriority(String actionPriority) {
            this.actionPriority = actionPriority;
        }

    }


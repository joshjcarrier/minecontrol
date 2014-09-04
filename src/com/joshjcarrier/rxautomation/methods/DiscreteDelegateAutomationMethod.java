package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.rxautomation.persistence.*;

public class DiscreteDelegateAutomationMethod implements IAutomationMethod {
    private final static String METHOD_ID = "discrete-delegate";
    private final static String PRIMARY_ID = "primary";
    private final static String SECONDARY_ID = "secondary";
    private final static String TERTIARY_ID = "tertiary";
    private final static String QUATERNARY_ID = "quaternary";

    private final IAutomationMethod primaryAutomationMethod;
    private final IAutomationMethod secondaryAutomationMethod;
    private final IAutomationMethod tertiaryAutomationMethod;
    private final IAutomationMethod quaternaryAutomationMethod;

    public DiscreteDelegateAutomationMethod(
            IAutomationMethod primaryAutomationMethod,
            IAutomationMethod secondaryAutomationMethod,
            IAutomationMethod tertiaryAutomationMethod,
            IAutomationMethod quaternaryAutomationMethod) {

        this.primaryAutomationMethod = primaryAutomationMethod;
        this.secondaryAutomationMethod = secondaryAutomationMethod;
        this.tertiaryAutomationMethod = tertiaryAutomationMethod;
        this.quaternaryAutomationMethod = quaternaryAutomationMethod;
    }

    public DiscreteDelegateAutomationMethod(
            IAutomationMethod primaryAutomationMethod,
            IAutomationMethod secondaryAutomationMethod)
    {
        this(primaryAutomationMethod, secondaryAutomationMethod, primaryAutomationMethod, primaryAutomationMethod);
    }

    public static IAutomationMethod load(IAutomationReader automationReader) {
        try {
            String methodId = automationReader.readMethod();
            if(!methodId.equalsIgnoreCase(METHOD_ID)) {
                return null;
            }

            IAutomationReader primaryAutomationReader = new NestedAutomationReader(PRIMARY_ID, automationReader);
            IAutomationMethod primaryAutomationMethod = AutomationMethodFactory.load(primaryAutomationReader);

            IAutomationReader secondaryAutomationReader = new NestedAutomationReader(SECONDARY_ID, automationReader);
            IAutomationMethod secondaryAutomationMethod = AutomationMethodFactory.load(secondaryAutomationReader);
            if(primaryAutomationMethod == null || secondaryAutomationMethod == null) {
                return null;
            }

            IAutomationReader tertiaryAutomationReader = new NestedAutomationReader(TERTIARY_ID, automationReader);
            IAutomationMethod tertiaryAutomationMethod = AutomationMethodFactory.load(tertiaryAutomationReader);
            if (tertiaryAutomationMethod == null) {
                tertiaryAutomationMethod = primaryAutomationMethod;
            }

            IAutomationReader quaternaryAutomationReader = new NestedAutomationReader(QUATERNARY_ID, automationReader);
            IAutomationMethod quaternaryAutomationMethod = AutomationMethodFactory.load(quaternaryAutomationReader);
            if (quaternaryAutomationMethod == null) {
                quaternaryAutomationMethod = primaryAutomationMethod;
            }

            return new DiscreteDelegateAutomationMethod(primaryAutomationMethod, secondaryAutomationMethod, tertiaryAutomationMethod, quaternaryAutomationMethod);
        } catch (Exception e) {
            return null;
        }
    }

    public IAutomationMethod getPrimaryAutomationMethod() {
        return this.primaryAutomationMethod;
    }

    public IAutomationMethod getSecondaryAutomationMethod() {
        return this.secondaryAutomationMethod;
    }

    public IAutomationMethod getTertiaryAutomationMethod() {
        return this.tertiaryAutomationMethod;
    }

    public IAutomationMethod getQuaternaryAutomationMethod() {
        return this.quaternaryAutomationMethod;
    }

    @Override
    public void automate(Float value) {
        if (value == 1f) {
            this.secondaryAutomationMethod.automate(0f);

            if (this.tertiaryAutomationMethod != null) {
                this.tertiaryAutomationMethod.automate(0f);
            }

            if (this.quaternaryAutomationMethod != null) {
                this.quaternaryAutomationMethod.automate(0f);
            }

            this.primaryAutomationMethod.automate(1f);
        } else if (value == -1f || (this.secondaryAutomationMethod != this.primaryAutomationMethod && value == 0.25f)) {
            this.primaryAutomationMethod.automate(0f);

            if (this.tertiaryAutomationMethod != null) {
                this.tertiaryAutomationMethod.automate(0f);
            }

            if (this.quaternaryAutomationMethod != null) {
                this.quaternaryAutomationMethod.automate(0f);
            }

            this.secondaryAutomationMethod.automate(1f);
        } else if (this.tertiaryAutomationMethod != this.primaryAutomationMethod && value == 0.5f) {
            this.primaryAutomationMethod.automate(0f);
            this.secondaryAutomationMethod.automate(0f);

            if (this.quaternaryAutomationMethod != null) {
                this.quaternaryAutomationMethod.automate(0f);
            }

            if (this.tertiaryAutomationMethod != null) {
                this.tertiaryAutomationMethod.automate(1f);
            }
        } else if (this.quaternaryAutomationMethod != this.primaryAutomationMethod && value == 0.75f) {
            this.primaryAutomationMethod.automate(0f);
            this.secondaryAutomationMethod.automate(0f);

            if (this.tertiaryAutomationMethod != null) {
                this.tertiaryAutomationMethod.automate(0f);
            }

            if (this.quaternaryAutomationMethod != null) {
                this.quaternaryAutomationMethod.automate(1f);
            }
        } else {
            this.primaryAutomationMethod.automate(0f);
            this.secondaryAutomationMethod.automate(0f);

            if (this.tertiaryAutomationMethod != null) {
                this.tertiaryAutomationMethod.automate(0f);
            }

            if (this.quaternaryAutomationMethod != null) {
                this.quaternaryAutomationMethod.automate(0f);
            }
        }
    }

    @Override
    public String getName() {
        return this.primaryAutomationMethod.getName()
                + "+" + this.secondaryAutomationMethod.getName()
                + "+" + this.tertiaryAutomationMethod.getName()
                + "+" + this.quaternaryAutomationMethod.getName();
    }

    @Override
    public void write(IAutomationWriter automationWriter) {
        automationWriter.writeMethod(METHOD_ID);
        IAutomationWriter primaryNestedAutomationWriter = new NestedAutomationWriter(PRIMARY_ID, automationWriter);
        this.primaryAutomationMethod.write(primaryNestedAutomationWriter);

        IAutomationWriter secondaryNestedAutomationWriter = new NestedAutomationWriter(SECONDARY_ID, automationWriter);
        this.secondaryAutomationMethod.write(secondaryNestedAutomationWriter);

        if (this.tertiaryAutomationMethod != this.primaryAutomationMethod) {
            IAutomationWriter tertiaryNestedAutomationWriter = new NestedAutomationWriter(TERTIARY_ID, automationWriter);
            this.tertiaryAutomationMethod.write(tertiaryNestedAutomationWriter);
        }

        if (this.quaternaryAutomationMethod != this.primaryAutomationMethod) {
            IAutomationWriter quaternaryNestedAutomationWriter = new NestedAutomationWriter(QUATERNARY_ID, automationWriter);
            this.quaternaryAutomationMethod.write(quaternaryNestedAutomationWriter);
        }
    }
}

package SpongeCity.EvaluationPlatform.Core.model;

/**
 * Created by saber on 2016/3/2.
 * 表示Excel中一个cell，包括cell对应的列名和值
 */
public class ParamDataModel {
    private String paramDisplayName;
    private String paramName;
    private double value;
    private boolean isCalculable;

    public boolean isCalculable() {
        return isCalculable;
    }

    public void setIsCalculable(boolean isCalculable) {
        this.isCalculable = isCalculable;
    }

    public String getParamDisplayName() {
        return paramDisplayName;
    }

    public void setParamDisplayName(String paramDisplayName) {
        this.paramDisplayName = paramDisplayName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

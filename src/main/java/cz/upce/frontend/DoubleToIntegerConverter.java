package cz.upce.frontend;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class DoubleToIntegerConverter implements Converter<Double, Integer> {

    private static final long serialVersionUID = 1L;

    @Override
    public Result<Integer> convertToModel(Double presentation, ValueContext valueContext) {
        if (presentation == null) {
            return Result.ok(null);
        } else {
            return Result.ok(presentation.intValue());
        }
    }

    @Override
    public Double convertToPresentation(Integer model, ValueContext valueContext) {
        if (model == null) {
            return null;
        } else {
            return Double.valueOf(model);
        }

    }

}

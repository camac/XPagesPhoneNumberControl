package com.gregorbyte.xsp.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.ibm.commons.util.StringUtil;

public class PhoneNumberConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		if (StringUtil.isEmpty(value))
			return value;

		PhoneNumberUtil util = PhoneNumberUtil.getInstance();
		String defCountryCode = "AU";

		try {

			PhoneNumber number = util.parse(value, defCountryCode);

			if (!util.isValidNumber(number)) {

				FacesMessage fm = new FacesMessage();
				fm.setSummary("Phone Number is not valid for country code "
						+ defCountryCode);
				fm.setDetail("The supplied Phone Number is not valid to the country code "
								+ defCountryCode);
				fm.setSeverity(FacesMessage.SEVERITY_ERROR);

				throw new ConverterException(fm);

			} else {
				return util.format(number, PhoneNumberFormat.INTERNATIONAL);
			}

		} catch (NumberParseException e) {

			FacesMessage fm = new FacesMessage();
			fm.setSummary("The Phone Number entered is not in a valid format for "
							+ defCountryCode);
			fm.setDetail(e.getMessage());
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ConverterException(fm);
		}
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value == null)
			return null;
		return value.toString();

	}

}

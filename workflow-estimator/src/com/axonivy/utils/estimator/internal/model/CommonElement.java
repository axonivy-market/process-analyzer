package com.axonivy.utils.estimator.internal.model;

import ch.ivyteam.ivy.process.model.BaseElement;
import ch.ivyteam.ivy.process.model.value.PID;

@SuppressWarnings("restriction")
public class CommonElement implements ProcessElement{
	private BaseElement element;

	public CommonElement(BaseElement element) {
		this.element = element;
	}
	
	@Override
	public boolean equals(Object obj) {	
		if(obj instanceof CommonElement) {
			return ((CommonElement)obj).element.equals(element);
		}
		return false;
	}
	
	@Override
	public int hashCode() {		
		return element.hashCode();
	}
	
	@Override
	public String toString() {
		return element.toString();
	}

	@Override
	public BaseElement getElement() {		
		return element;
	}
	
	@Override
	public PID getPid() {
		return element.getPid();
	}
}

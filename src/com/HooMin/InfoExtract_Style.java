package com.HooMin;

import java.util.HashMap;

import ke.RefSet;

class RefSet_Style extends RefSet {
	String style;
}

public class InfoExtract_Style {
	public InfoExtract_Style() {
	}

	String classifier_by_style;

	// public void InfoExtract_Style(RefSet_Style aRefSet_Style, HashMap<String,
	// CRFmodel> aCRFmodel) {
	// if (classifier_by_style == "")
	// Classifier.learn(traindata, modelname);
	// Classifier.classify(aRef, modelname);
	// InfoExtract.InfoExtract(aRefSet_Style,
	// aCRFmodel.get(aRefSet_Style.style));
	// }

	public static void main(String[] args) {
		RefSet_Style aRefSet_Style = new RefSet_Style();
		HashMap<String, CRFmodel> aCRFmodel = new HashMap<String, CRFmodel>();
		InfoExtract_Style aThisClass = new InfoExtract_Style();
		// aThisClass.InfoExtract_Style(aRefSet_Style, aCRFmodel);
	}
}
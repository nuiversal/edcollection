package com.winway.android.edcollection.test;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;

import android.widget.Button;

public class TestMainViewHolder extends BaseViewHolder {
	@ViewInject(R.id.button1)
	private Button label;
	@ViewInject(R.id.button2)
	private Button intermediate;
	@ViewInject(R.id.button3)
	private Button tower;
	@ViewInject(R.id.button4)
	private Button transformer;
	@ViewInject(R.id.button5)
	private Button workwell;
	@ViewInject(R.id.button6)
	private Button linename;
	@ViewInject(R.id.power)
	private Button power;
	@ViewInject(R.id.sub)
	private Button sub;
	@ViewInject(R.id.mark)
	private Button mark;
	@ViewInject(R.id.cabinet)
	private Button cabinet;

	public Button getPower() {
		return power;
	}

	public void setPower(Button power) {
		this.power = power;
	}

	public Button getSub() {
		return sub;
	}

	public void setSub(Button sub) {
		this.sub = sub;
	}

	public Button getMark() {
		return mark;
	}

	public void setMark(Button mark) {
		this.mark = mark;
	}

	public Button getCabinet() {
		return cabinet;
	}

	public void setCabinet(Button cabinet) {
		this.cabinet = cabinet;
	}

	public Button getLabel() {
		return label;
	}

	public void setLabel(Button label) {
		this.label = label;
	}

	public Button getIntermediate() {
		return intermediate;
	}

	public void setIntermediate(Button intermediate) {
		this.intermediate = intermediate;
	}

	public Button getTower() {
		return tower;
	}

	public void setTower(Button tower) {
		this.tower = tower;
	}

	public Button getTransformer() {
		return transformer;
	}

	public void setTransformer(Button transformer) {
		this.transformer = transformer;
	}

	public Button getWorkwell() {
		return workwell;
	}

	public void setWorkwell(Button workwell) {
		this.workwell = workwell;
	}

	public Button getLinename() {
		return linename;
	}

	public void setLinename(Button linename) {
		this.linename = linename;
	}

}

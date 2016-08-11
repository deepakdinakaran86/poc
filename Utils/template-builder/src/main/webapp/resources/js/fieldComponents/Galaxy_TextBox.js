function GalaxyTextBox(text_obj) {

	this.name = text_obj.name;
	this.id = text_obj.id;
	this.mandatory = this.check_flag(text_obj.mandatory);
	this.label = this.check_label(text_obj.label);
	this.text_style = text_obj.text_style;
	this.label_style = text_obj.label_style;
	this.container = text_obj.container;
	this.placeholder = text_obj.placeholder;
	this.draggable = this.check_flag(text_obj.draggable);
	this.server_validation = this.check_flag(text_obj.server_validation);
	this.disabled = this.check_flag(text_obj.disabled);
	this.onClick = text_obj.onClick;
	this.mydata = text_obj;
	this.draw_div();
}

GalaxyTextBox.prototype.check_flag = function(object) {
	var flag = (typeof (object) == 'undefined' || object == null) ? false
			: object;
	return flag;
};

GalaxyTextBox.prototype.check_label = function(object) {
	var label = (typeof (object) == 'undefined' || object == null) ? "Label Text"
			: object;
	return label;
};

GalaxyTextBox.prototype.draw_text = function() {
	var text = $("<input>", {
		type : 'text',
		placeholder : this.placeholder,
		name : this.name,
		id : 'text_' +this.id,
		style : this.text_style
	});

	if (this.mandatory) {
		text.attr('required', 'required');
	}
	if (this.disabled) {
		text.attr('readonly', 'readonly');
	}

	if (this.server_validation) {
		text.keyup(null, fn_server_validation);
	}

	return text;

};

GalaxyTextBox.prototype.draw_label = function() {
	var label = $("<label>", {
		id : 'label_' + this.id,
		style : this.label_style
	}).text(this.label);
	if (this.mandatory) {
		var span = $("<span>", {
			id : 'label_' + this.id,
			style : "color:red;font-weight:bold"
		}).text('*');
		label.append(span);
	}
	return label;
};

GalaxyTextBox.prototype.draw_drag = function() {
	var drag_id = "drag_" + this.id;
	$(function() {
		$("#" + drag_id).draggable({
			containment : "#container"
		});
	});

	return $("<span>", {
		id : drag_id,
		class : 'ui-draggable',
		style : 'position: relative; left: 18px; top: 18px;'
	});
};

GalaxyTextBox.prototype.draw_div = function() {
	var label = this.draw_label();
	var input = this.draw_text();

	input.appendTo(label);
	$("#" + this.container).empty();
	if (this.draggable) {
		var span = this.draw_drag();
		span.append(label);
		$("#" + this.container).append(span);
	} else {
		$("#" + this.container).append(label);
	}
	 $("#" + this.container).append("<img src =\"resources/images/delete_form_field.png\" onclick=\"onDeleteComponenetClick('"+this.container+"')\"> </img>");
	 $("#" + this.container).attr('my-data',JSON.stringify(this.mydata));

};

function fn_server_validation(object) {
	alert(object.target.value);
}

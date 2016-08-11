/**
 * 
 * Galaxy namespace which exposes galaxy specific utilies and components.
 * @author: pcseg327 ismail
 *  
 */

var galaxy = galaxy || {};

(function(galaxy) {

	galaxy.utils = galaxy.utils || {};
	galaxy.utils.myAlert = function(text) {
		alert(text);
	};

	var modalBoxTemplate = '<div id="galaxyModalMessageBox"><div class="gx-modal-body">#= data.text #</div><div class="gx-modal-footer"> # for (var i = 0; i < data.buttons.length; i++) { # <button type="button" onclick=" #=data.buttons[i].handler# " class="gx-modal-btn #= data.buttons[i].className # "> #= data.buttons[i].name # </button> # } # </div></div>';
	var modalMessageTemplate = kendo.template(modalBoxTemplate);

	var modalMessage = function(customOptions) {
		var options = {
			title : '',
			text : '',
			level : 'INFO',
			buttons : [ {
				name : 'OK',
				handler : '',
				className : 'k-button k-primary delete-cancel'
			} ]
		};
		$.extend(options, customOptions);

		$("#galaxyModalMessageBox").remove();
		$('body').append(modalMessageTemplate(options));

		var titleStyle = 'k-info-colored';

		if ('ERROR'.toLowerCase() === options.level.toLowerCase()) {
			titleStyle = 'k-error-colored';
		} else if ('WARNING'.toLowerCase() === options.level.toLowerCase()) {
			titleStyle = 'k-info-colored';
		} else if ('SUCCESS'.toLowerCase() === options.level.toLowerCase()) {
			titleStyle = 'k-success-colored';
		}

		var kendoWindowElement = $("#galaxyModalMessageBox").kendoWindow({
			title : options.title,
			modal : true,
			draggable : false,
			pinned : true,
			resizable : false,
			actions : []

		});

		var kendoWindow = kendoWindowElement.data("kendoWindow");
		kendoWindow.center().open();
		kendoWindowElement.parent().find('.k-window-titlebar').addClass(titleStyle);
		kendoWindowElement.find(".delete-cancel").click(function() {
			kendoWindow.close();
		}).end();

	};

	/**
	 * Show galaxy styled alert box
	 * 
	 * @param title text
	 * @param content text
	 * @param level - {success|error|warning|info}
	 * @param button text
	 * @param onclick handler code
	 * 
	 * @usage: galaxy.alert('alertTitle', 'watever content', 'error', 'Ok', 'hello();' );
	 * 
	 */
	galaxy.alert = function(title, content, level, acceptButtonText,
			acceptHandler) {
		var options = {
			title : title,
			text : content,
			level : level ? level : 'INFO',
			buttons : [ {
				name : acceptButtonText,
				handler : acceptHandler,
				className : 'k-button k-primary delete-cancel'
			} ]
		};
		modalMessage(options);
	};

	/**
	 * Show galaxy styled confirm box
	 * 
	 * @param title text
	 * @param content text
	 * @param level - {success|error|warning|info}
	 * @param accept button text
	 * @param accept onclick handler code
	 * @param cancel button text
	 * @param cancel onclick handler code
	 * @usage: galaxy.confirm('ConfirmTitle', 'Watever content', 'error', 'Yes', 'hello();', 'No', null);
	 * 
	 */
	galaxy.confirm = function(title, content, level, acceptButtonText,
			acceptHandler, cancelButtonText, cancelHandler) {
		var options = {
			title : title,
			text : content,
			level : level ? level : 'INFO',
			buttons : [ {
				name : acceptButtonText,
				handler : acceptHandler,
				className : 'k-button delete-cancel'
			}, {
				name : cancelButtonText,
				handler : cancelHandler,
				className : 'k-button delete-cancel'
			} ]
		};
		modalMessage(options);
	};
})(galaxy);

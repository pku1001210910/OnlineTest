$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
	options.async = true;
});

var isInputSupported = 'placeholder' in document.createElement('input');
var isTextareaSupported = 'placeholder' in document.createElement('textarea');
if (!isInputSupported || !isTextareaSupported) {
    $('[placeholder]').focus(function () {
        var input = $(this);
        if (input.val() == input.attr('placeholder') && input.data('placeholder')) {
            input.val('');
        }
    }).blur(function () {
        var input = $(this);
        if (input.val() == '') {
            input.val(input.attr('placeholder'));
            input.data('placeholder', true);
        } else {
            input.data('placeholder', false);
        }
    }).blur().parents('form').submit(function () {
        $(this).find('[placeholder]').each(function () {
            var input = $(this);
            if (input.val() == input.attr('placeholder') && input.data('placeholder')) {
                input.val('');
            }
        })
    });
}

String.prototype.trim = function () {
	return this.replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
}
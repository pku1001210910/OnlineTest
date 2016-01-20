// load page based on selected menu
$('.admin-menu .admin-menu-item').on('click', function(e) {
	// load selected page
	var $li = $(e.currentTarget); 
	loadPage($li.data('target'));

	// menu selected effects
	$('.admin-menu .admin-menu-item').removeClass('active');
	$li.addClass('active');
	
	e.preventDefault();
});

function loadPage(target) {
	var targetAction = './' + target + '.action';
	$('#main-content').val('');
	$('#main-content').load(targetAction);
}

loadPage('welcome');
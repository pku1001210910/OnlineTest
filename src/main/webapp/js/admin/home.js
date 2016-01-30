// load page based on selected menu
$('.admin-menu .admin-menu-item').on('click', function(e) {
	// clear current page
	$('#main-content').empty();
	
	// load selected page
	var $li = $(e.currentTarget); 
	loadPage($li.data('target'));

	// menu selected effects
	$('.admin-menu .admin-menu-item').removeClass('active');
	$li.addClass('active');
	
	e.preventDefault();
});

var times = 1;
function loadPage(target) {
	var targetAction = './' + target + '.action' + '?times=' + (times++);
	$('#main-content').load(targetAction);
}

loadPage('welcome');
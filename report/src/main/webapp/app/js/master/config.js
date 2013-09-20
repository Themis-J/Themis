'use strict';


// Declare app level module which depends on filters, and services
angular.module('master.config', []).constant(
	'config',
	{
		service : {
			url : '/themis/dealer/report', 
			localUrl : 'reports'
		}

	}
);
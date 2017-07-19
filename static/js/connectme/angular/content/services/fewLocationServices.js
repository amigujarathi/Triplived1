

projectModule.factory('FewLocationService', function($http) {
	var loaded = '';
	
	return {
        setLoad: function(variableId) {
            loaded = variableId;
        },
        isLoad: function() {
            return loaded;
        },
	};
	
});

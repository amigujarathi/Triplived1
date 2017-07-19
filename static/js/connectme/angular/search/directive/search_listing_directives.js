
projectModule.directive('slideUp', function () {
    var linker = function (scope, element, attrs) {
        element.on('click', function () {
            $(this).parent().slideUp('slow');
        });
    };

    return {
        restrict:'A',
        link:linker
    }
});

projectModule.directive('closeLoginMessage', function () {
    var linker = function (scope, element, attrs) {
        element.on('click', function () {
        	$( "#loginMessageId" ).hide("slow");
        	$("#loginMessageId").remove();
        	
        });
    };

    return {
        restrict:'A',
        link:linker
    }
});

projectModule.directive('slideUpButton', function () {
    var linker = function (scope, element, attrs) {
        element.on('click', function () {
            $(this).parent().parent().slideUp('slow');
        });
    };

    return {
        restrict:'A',
        link:linker
    }
});
projectModule.directive('slideDown', function () {
    var linker = function (scope, element, attrs) {
        element.on('click', function () {
            $("#searchWidgetId").slideDown('slow');
        });
    };

    return {
        restrict:'A',
        link:linker
    }
});
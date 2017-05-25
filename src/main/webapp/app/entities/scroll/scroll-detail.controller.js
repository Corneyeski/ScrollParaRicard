(function() {
    'use strict';

    angular
        .module('pruebaScrollApp')
        .controller('ScrollDetailController', ScrollDetailController);

    ScrollDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Scroll'];

    function ScrollDetailController($scope, $rootScope, $stateParams, previousState, entity, Scroll) {
        var vm = this;

        vm.scroll = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pruebaScrollApp:scrollUpdate', function(event, result) {
            vm.scroll = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

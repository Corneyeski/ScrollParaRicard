(function() {
    'use strict';

    angular
        .module('pruebaScrollApp')
        .controller('ScrollDialogController', ScrollDialogController);

    ScrollDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Scroll'];

    function ScrollDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Scroll) {
        var vm = this;

        vm.scroll = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.scroll.id !== null) {
                Scroll.update(vm.scroll, onSaveSuccess, onSaveError);
            } else {
                Scroll.save(vm.scroll, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pruebaScrollApp:scrollUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

(function() {
    'use strict';

    angular
        .module('pruebaScrollApp')
        .controller('ScrollDeleteController',ScrollDeleteController);

    ScrollDeleteController.$inject = ['$uibModalInstance', 'entity', 'Scroll'];

    function ScrollDeleteController($uibModalInstance, entity, Scroll) {
        var vm = this;

        vm.scroll = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Scroll.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CervesaDeleteController',CervesaDeleteController);

    CervesaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cervesa'];

    function CervesaDeleteController($uibModalInstance, entity, Cervesa) {
        var vm = this;
        vm.cervesa = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Cervesa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

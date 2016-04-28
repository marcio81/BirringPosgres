(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('EvaluarDeleteController',EvaluarDeleteController);

    EvaluarDeleteController.$inject = ['$uibModalInstance', 'entity', 'Evaluar'];

    function EvaluarDeleteController($uibModalInstance, entity, Evaluar) {
        var vm = this;
        vm.evaluar = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Evaluar.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

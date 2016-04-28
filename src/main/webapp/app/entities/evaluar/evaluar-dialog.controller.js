(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('EvaluarDialogController', EvaluarDialogController);

    EvaluarDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Evaluar', 'Cervesa', 'User'];

    function EvaluarDialogController ($scope, $stateParams, $uibModalInstance, entity, Evaluar, Cervesa, User) {
        var vm = this;
        vm.evaluar = entity;
        vm.cervesas = Cervesa.query();
        vm.users = User.query();
        vm.load = function(id) {
            Evaluar.get({id : id}, function(result) {
                vm.evaluar = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterApp:evaluarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.evaluar.id !== null) {
                Evaluar.update(vm.evaluar, onSaveSuccess, onSaveError);
            } else {
                Evaluar.save(vm.evaluar, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

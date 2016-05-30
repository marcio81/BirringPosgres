(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CervesaDialogController', CervesaDialogController);

    CervesaDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Cervesa', 'Precio', 'Evaluar', 'Comentario', 'Ubicacion'];

    function CervesaDialogController ($scope, $stateParams, $uibModalInstance, DataUtils, entity, Cervesa, Precio, Evaluar, Comentario, Ubicacion) {
        var vm = this;
        vm.cervesa = entity.Cervesa;
       // vm.precio = entity.Precio;

        vm.precios = Precio.query();
        vm.evaluars = Evaluar.query();
        vm.comentarios = Comentario.query();
        vm.ubicacions = Ubicacion.query();
        vm.load = function(id) {

            Cervesa.get({id : id}, function(result) {
                vm.cervesa = result;

            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterApp:cervesaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {


            vm.isSaving = true;
            if (vm.cervesa.id !== null) {
                Cervesa.update(vm.cervesa, onSaveSuccess, onSaveError);
                // Precio.update(vm.precio, onSaveSuccess, onSaveError);
                //Precio.update(vm.precio, onSaveSuccess, onSaveError);

            } else {
                Cervesa.save(vm.cervesa, onSaveSuccess, onSaveError);
                // Precio.save(vm.precio, onSaveSuccess, onSaveError);
                //Precio.save(vm.precio, onSaveSuccess, onSaveError);



            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.setFoto = function ($file, cervesa) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        cervesa.foto = base64Data;
                        cervesa.fotoContentType = $file.type;
                    });
                });
            }
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
    }
})();

(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('MapaController', MapaController);

    MapaController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ubicacion', 'Precio', 'Cervesa', 'ngMap'];

    function MapaController ($scope, $stateParams, $uibModalInstance, entity, Ubicacion, Precio, Cervesa, NgMap) {
        var vm = this;
        vm.ubicacion = entity;
        vm.precios = Precio.query();
        vm.cervesas = Cervesa.query();

        vm.types = "['establishment']";
        vm.placeChanged = function() {
            vm.place = this.getPlace();
            console.log('location', vm.place.geometry.location);
            vm.map.setCenter(vm.place.geometry.location);
        }
        NgMap.getMap().then(function(map) {
            vm.map = map;
        });

        vm.load = function(id) {
            Ubicacion.get({id : id}, function(result) {
                vm.ubicacion = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterApp:ubicacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.ubicacion.id !== null) {
                Ubicacion.update(vm.ubicacion, onSaveSuccess, onSaveError);
            } else {
                Ubicacion.save(vm.ubicacion, onSaveSuccess, onSaveError);
            }
        };


    }
})();

'use strict';

describe('Controller Tests', function() {

    describe('Cervesa Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCervesa, MockPrecio, MockEvaluar, MockComentario, MockUbicacion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCervesa = jasmine.createSpy('MockCervesa');
            MockPrecio = jasmine.createSpy('MockPrecio');
            MockEvaluar = jasmine.createSpy('MockEvaluar');
            MockComentario = jasmine.createSpy('MockComentario');
            MockUbicacion = jasmine.createSpy('MockUbicacion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cervesa': MockCervesa,
                'Precio': MockPrecio,
                'Evaluar': MockEvaluar,
                'Comentario': MockComentario,
                'Ubicacion': MockUbicacion
            };
            createController = function() {
                $injector.get('$controller')("CervesaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterApp:cervesaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

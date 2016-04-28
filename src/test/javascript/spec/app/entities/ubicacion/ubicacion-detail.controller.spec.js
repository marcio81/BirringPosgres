'use strict';

describe('Controller Tests', function() {

    describe('Ubicacion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUbicacion, MockPrecio, MockCervesa;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUbicacion = jasmine.createSpy('MockUbicacion');
            MockPrecio = jasmine.createSpy('MockPrecio');
            MockCervesa = jasmine.createSpy('MockCervesa');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Ubicacion': MockUbicacion,
                'Precio': MockPrecio,
                'Cervesa': MockCervesa
            };
            createController = function() {
                $injector.get('$controller')("UbicacionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterApp:ubicacionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

package com.joshjcarrier.rxjinput;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import rx.Observable;
import rx.observables.ConnectableObservable;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class RxController {
    private final Controller controller;
    private final ConnectableObservable<Event> events;

    public RxController(final Controller controller) {
        this.controller = controller;

        this.events = Observable
                .interval(5, TimeUnit.MILLISECONDS)
                .flatMap(new Func1<Long, Observable<? extends Event>>() {


                    {
                        // primes the event queue with pending controller events
                        //controller.poll();
                        //this.queue = controller.getEventQueue();
                    }

                    public Observable<Event> call(Long arg0) {
                        controller.poll();
                        EventQueue queue = controller.getEventQueue();
                        Collection<Event> events = new ArrayList<Event>();

                        Event event = new Event();
                        while (queue.getNextEvent(event)) {
                            events.add(event);
                            event = new Event(); // this allocation necessary?
                        }

                        return Observable.from(events);
                    }
                })
                .publish(); // allow multiple observables to draw from single poll source
    }

    @Deprecated // only for transition purposes
    public Controller getInternalController() {
        return this.controller;
    }

    public Observable<Float> getComponent(final Component.Identifier id) {
        this.events.connect();

        return this.events
                .filter(new Func1<Event, Boolean>() {
                    @Override
                    public Boolean call(Event event) {
                        return event.getComponent().getIdentifier() == id;
                    }
                })
                .map(new Func1<Event, Float>() {
                    @Override
                    public Float call(Event event) {
                        Float data = event.getComponent().getPollData();

                        // debounce joystick
                        if(data > 0 && data <= 0.07) {
                            data = 0f;
                        }
                        else if(-data > 0 && -data <= 0.07) {
                            data = 0f;
                        }

                        return data;
                    }
                });
    }

    public Observable<Event> getEvents() {
        return this.events;
    }

    public String getName() { return this.controller.getName(); }
}

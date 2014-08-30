package com.joshjcarrier.minecontrol.com.joshjcarrier.rxjinput;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import rx.Observable;
import rx.util.functions.Func1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class RxController {
    private final Controller controller;

    public RxController(Controller controller) {
        this.controller = controller;
    }

    @Deprecated // only for transition purposes
    public Controller getInternalController() {
        return this.controller;
    }

    public Observable<Event> getEvents() {
        return Observable
                .interval(5, TimeUnit.MILLISECONDS)
                .flatMap(new Func1<Long, Observable<? extends Event>>() {
                    EventQueue queue;

                    {
                        // primes the event queue with pending controller events
                        controller.poll();
                        this.queue = controller.getEventQueue();
                    }

                    public Observable<Event> call(Long arg0) {

                        Collection<Event> events = new ArrayList<Event>();

                        Event event = new Event();
                        while (queue.getNextEvent(event)) {
                            events.add(event);
                            event = new Event(); // this allocation necessary?
                        }

                        return Observable.from(events);
                    }
                });
    }
}

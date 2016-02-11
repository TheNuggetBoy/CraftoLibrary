/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.time.Duration;

import javax.annotation.Nullable;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 06.10.2015
 */
public class ConditionalExecutor {

	@Nullable private final Duration durationCondition;
	@Nullable private final Integer maxExecutionsCondition;

	private long lastExecution = 0;
	private int executions = 0;

	private long disabledSince = 0;
	private long disabledFor = 0;

	private final Runnable runnable;

	/** TODO: Documentation */
	public ConditionalExecutor(final Duration durationCondition, final Integer maxExecutionsCondition, final Runnable runnable) {
		Check.nonNulls("The durationCondition/maxExecutionsCondition/runnable cannot be null!", durationCondition, maxExecutionsCondition, runnable);
		this.durationCondition = durationCondition;
		this.maxExecutionsCondition = maxExecutionsCondition;
		this.runnable = runnable;
	}

	/** TODO: Documentation */
	public ConditionalExecutor(final Duration durationCondition, final Runnable runnable) {
		Check.nonNulls("The durationCondition/runnable cannot be null!", durationCondition, runnable);
		this.durationCondition = durationCondition;
		this.maxExecutionsCondition = null;
		this.runnable = runnable;
	}

	/** TODO: Documentation */
	public ConditionalExecutor(final Integer maxExecutionsCondition, final Runnable runnable) {
		Check.nonNulls("The maxExecutionsCondition/runnable cannot be null!", maxExecutionsCondition, runnable);
		this.durationCondition = null;
		this.maxExecutionsCondition = maxExecutionsCondition;
		this.runnable = runnable;
	}

	/** TODO: Documentation */
	public int getExecutions() { return this.executions; }

	/** TODO: Documentation */
	public long getLastExecution() { return this.lastExecution; }

	/** TODO: Documentation */
	public boolean isDisabled() {
		return this.disabledSince > 0 && System.currentTimeMillis() - this.disabledSince < this.disabledFor;
	}

	/** TODO: Documentation */
	public void disableFor(final Duration duration) {
		this.disabledSince = System.currentTimeMillis();
		this.disabledFor = duration.toMillis();
	}

	/** TODO: Documentation */
	public void execute() {
		if (this.disabledSince > 0) {
			if (this.isDisabled()) { return; }
			else { this.disabledSince = 0; }
		}

		if (this.durationCondition != null && System.currentTimeMillis() - this.lastExecution <= this.durationCondition.toMillis()) { return; }

		if (this.maxExecutionsCondition != null && this.executions >= this.maxExecutionsCondition) { return; }

		this.executions++;
		this.lastExecution = System.currentTimeMillis();
		this.runnable.run();
	}

}
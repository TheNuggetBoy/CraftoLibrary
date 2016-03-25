package de.craftolution.craftolibrary.data.result;

import java.util.Optional;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.data.Data;
import de.craftolution.craftolibrary.data.DataStorage;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class DataTransactionResult {

	/** TODO: Documentation */
	public static enum ResultType {

		/** TODO: Documentation */
		SUCCESS,

		/** TODO: Documentation */
		FAILURE;

	}

	private final DataStorage storage;
	private final ResultType type;

	@Nullable private final FailureReason reason;
	@Nullable private final Data offeredData;
	@Nullable private final Data replacedData;

	public DataTransactionResult(final DataStorage storage, final ResultType type, final FailureReason reason, final Data offeredData, final Data replacedData) {
		this.storage = storage;
		this.reason = reason;
		this.type = type;
		this.offeredData = offeredData;
		this.replacedData = replacedData;
	}

	/** TODO: Documentation */
	public boolean isSuccess() { return this.getResultType().equals(ResultType.SUCCESS); }

	/** TODO: Documentation */
	public boolean isFailure() { return this.getResultType().equals(ResultType.FAILURE); }

	/** TODO: Documentation */
	public DataStorage getStorage() { return this.storage; }

	/** TODO: Documentation */
	public ResultType getResultType() { return this.type; }

	/** TODO: Documentation */
	public Optional<FailureReason> getFailureReason() { return Optional.ofNullable(this.reason); }

	/** TODO: Documentation */
	public Optional<Data> getOfferedData() { return Optional.ofNullable(this.offeredData); }

	/** TODO: Documentation */
	public Optional<Data> getReplacedData() { return Optional.ofNullable(this.replacedData); }

	/** TODO: Documentation */
	public DataTransactionResult rollback() {
		if (this.type.equals(ResultType.SUCCESS)) {
			if (this.replacedData != null) { return this.storage.offer(this.replacedData); }
			else { return this.storage.remove(this.offeredData.getClass()); }
		}
		else {
			return new DataTransactionResult(this.storage, ResultType.FAILURE, FailureReasons.CANT_ROLLBACK_FAILED_TRANSACTION, this.replacedData, this.offeredData);
		}
	}

}